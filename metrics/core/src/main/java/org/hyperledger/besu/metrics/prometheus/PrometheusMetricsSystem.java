/*
 * Copyright ConsenSys AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 */
package org.hyperledger.besu.metrics.prometheus;

import org.hyperledger.besu.metrics.ObservableMetricsSystem;
import org.hyperledger.besu.metrics.Observation;
import org.hyperledger.besu.metrics.StandardMetricCategory;
import org.hyperledger.besu.metrics.noop.NoOpMetricsSystem;
import org.hyperledger.besu.plugin.services.metrics.ExternalSummary;
import org.hyperledger.besu.plugin.services.metrics.LabelledMetric;
import org.hyperledger.besu.plugin.services.metrics.LabelledSuppliedMetric;
import org.hyperledger.besu.plugin.services.metrics.MetricCategory;
import org.hyperledger.besu.plugin.services.metrics.OperationTimer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.google.common.cache.Cache;
import com.google.common.collect.ImmutableSet;
import io.prometheus.client.Collector;
import io.prometheus.client.Collector.MetricFamilySamples;
import io.prometheus.client.Collector.MetricFamilySamples.Sample;
import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;
import io.prometheus.client.guava.cache.CacheMetricsCollector;
import io.prometheus.client.hotspot.BufferPoolsExports;
import io.prometheus.client.hotspot.ClassLoadingExports;
import io.prometheus.client.hotspot.GarbageCollectorExports;
import io.prometheus.client.hotspot.MemoryPoolsExports;
import io.prometheus.client.hotspot.StandardExports;
import io.prometheus.client.hotspot.ThreadExports;
import io.vertx.core.impl.ConcurrentHashSet;

/** The Prometheus metrics system. */
public class PrometheusMetricsSystem implements ObservableMetricsSystem {
  private static final List<String> EXTERNAL_SUMMARY_LABELS = List.of("quantile");

  private final Map<MetricCategory, Collection<Collector>> collectors = new ConcurrentHashMap<>();
  private final CollectorRegistry registry = new CollectorRegistry(true);
  private final Map<String, LabelledMetric<org.hyperledger.besu.plugin.services.metrics.Counter>>
      cachedCounters = new ConcurrentHashMap<>();
  private final Map<String, LabelledMetric<OperationTimer>> cachedTimers =
      new ConcurrentHashMap<>();
  private final Set<String> totalSuffixedCounters = new ConcurrentHashSet<>();
  private final Map<MetricCategory, CacheMetricsCollector> guavaCacheCollectors =
      new ConcurrentHashMap<>();
  private final Set<String> guavaCacheNames = new ConcurrentHashSet<>();

  private final Set<MetricCategory> enabledCategories;
  private final boolean timersEnabled;

  /**
   * Instantiates a new Prometheus metrics system.
   *
   * @param enabledCategories the enabled categories
   * @param timersEnabled the timers enabled
   */
  public PrometheusMetricsSystem(
      final Set<MetricCategory> enabledCategories, final boolean timersEnabled) {
    this.enabledCategories = ImmutableSet.copyOf(enabledCategories);
    this.timersEnabled = timersEnabled;
  }

  /** Init. */
  public void init() {
    if (isCategoryEnabled(StandardMetricCategory.PROCESS)) {
      registerCollector(StandardMetricCategory.PROCESS, new StandardExports());
    }
    if (isCategoryEnabled(StandardMetricCategory.JVM)) {
      registerCollector(StandardMetricCategory.JVM, new MemoryPoolsExports());
      registerCollector(StandardMetricCategory.JVM, new BufferPoolsExports());
      registerCollector(StandardMetricCategory.JVM, new GarbageCollectorExports());
      registerCollector(StandardMetricCategory.JVM, new ThreadExports());
      registerCollector(StandardMetricCategory.JVM, new ClassLoadingExports());
    }
  }

  @Override
  public Set<MetricCategory> getEnabledCategories() {
    return enabledCategories;
  }

  @Override
  public LabelledMetric<org.hyperledger.besu.plugin.services.metrics.Counter> createLabelledCounter(
      final MetricCategory category,
      final String name,
      final String help,
      final String... labelNames) {
    final String metricName = convertToPrometheusCounterName(category, name);
    return cachedCounters.computeIfAbsent(
        metricName,
        (k) -> {
          if (isCategoryEnabled(category)) {
            final Counter counter = Counter.build(metricName, help).labelNames(labelNames).create();
            registerCollector(category, counter);
            return new PrometheusCounter(counter);
          } else {
            return NoOpMetricsSystem.getCounterLabelledMetric(labelNames.length);
          }
        });
  }

  @Override
  public LabelledMetric<OperationTimer> createLabelledTimer(
      final MetricCategory category,
      final String name,
      final String help,
      final String... labelNames) {
    final String metricName = convertToPrometheusName(category, name);
    return cachedTimers.computeIfAbsent(
        metricName,
        (k) -> {
          if (timersEnabled && isCategoryEnabled(category)) {
            final Summary summary =
                Summary.build(metricName, help)
                    .quantile(0.2, 0.02)
                    .quantile(0.5, 0.05)
                    .quantile(0.8, 0.02)
                    .quantile(0.95, 0.005)
                    .quantile(0.99, 0.001)
                    .quantile(1.0, 0)
                    .labelNames(labelNames)
                    .create();
            registerCollector(category, summary);
            return new PrometheusTimer(summary);
          } else {
            return NoOpMetricsSystem.getOperationTimerLabelledMetric(labelNames.length);
          }
        });
  }

  @Override
  public LabelledMetric<OperationTimer> createSimpleLabelledTimer(
      final MetricCategory category,
      final String name,
      final String help,
      final String... labelNames) {
    final String metricName = convertToPrometheusName(category, name);
    return cachedTimers.computeIfAbsent(
        metricName,
        (k) -> {
          if (timersEnabled && isCategoryEnabled(category)) {
            final Histogram histogram =
                Histogram.build(metricName, help).labelNames(labelNames).buckets(1D).create();
            registerCollector(category, histogram);
            return new PrometheusSimpleTimer(histogram);
          } else {
            return NoOpMetricsSystem.getOperationTimerLabelledMetric(labelNames.length);
          }
        });
  }

  @Override
  public void createGauge(
      final MetricCategory category,
      final String name,
      final String help,
      final DoubleSupplier valueSupplier) {
    final String metricName = convertToPrometheusName(category, name);
    if (isCategoryEnabled(category)) {
      final Collector collector = new CurrentValueCollector(metricName, help, valueSupplier);
      registerCollector(category, collector);
    }
  }

  @Override
  public void trackExternalSummary(
      final MetricCategory category,
      final String name,
      final String help,
      final Supplier<ExternalSummary> summarySupplier) {
    if (isCategoryEnabled(category)) {
      final var externalSummaryCollector =
          new Collector() {
            @Override
            public List<MetricFamilySamples> collect() {
              final var externalSummary = summarySupplier.get();

              final var quantileValues =
                  externalSummary.quantiles().stream()
                      .map(
                          quantile ->
                              new Sample(
                                  name,
                                  EXTERNAL_SUMMARY_LABELS,
                                  List.of(Double.toString(quantile.quantile())),
                                  quantile.value()))
                      .toList();

              return List.of(
                  new MetricFamilySamples(
                      name, Type.SUMMARY, "RocksDB histogram for " + name, quantileValues));
            }
          };

      registerCollector(category, externalSummaryCollector);
    }
  }

  @Override
  public void createGuavaCacheCollector(
      final MetricCategory category, final String name, final Cache<?, ?> cache) {
    if (isCategoryEnabled(category)) {
      if (guavaCacheNames.contains(name)) {
        throw new IllegalStateException("Cache already registered: " + name);
      }
      guavaCacheNames.add(name);
      final var guavaCacheCollector =
          guavaCacheCollectors.computeIfAbsent(
              category,
              unused -> {
                final var cmc = new CacheMetricsCollector();
                registerCollector(category, cmc);
                return cmc;
              });
      guavaCacheCollector.addCache(name, cache);
    }
  }

  @Override
  public LabelledSuppliedMetric createLabelledSuppliedCounter(
      final MetricCategory category,
      final String name,
      final String help,
      final String... labelNames) {
    return createLabelledSuppliedMetric(category, Collector.Type.COUNTER, name, help, labelNames);
  }

  @Override
  public LabelledSuppliedMetric createLabelledSuppliedGauge(
      final MetricCategory category,
      final String name,
      final String help,
      final String... labelNames) {
    return createLabelledSuppliedMetric(category, Collector.Type.GAUGE, name, help, labelNames);
  }

  private LabelledSuppliedMetric createLabelledSuppliedMetric(
      final MetricCategory category,
      final Collector.Type type,
      final String name,
      final String help,
      final String... labelNames) {
    final String metricName = convertToPrometheusName(category, name);
    if (isCategoryEnabled(category)) {
      final PrometheusSuppliedValueCollector suppliedValueCollector =
          new PrometheusSuppliedValueCollector(type, metricName, help, List.of(labelNames));
      registerCollector(category, suppliedValueCollector);
      return suppliedValueCollector;
    }
    return NoOpMetricsSystem.getLabelledSuppliedMetric(labelNames.length);
  }

  private void registerCollector(final MetricCategory category, final Collector collector) {
    final Collection<Collector> categoryCollectors =
        this.collectors.computeIfAbsent(
            category, key -> Collections.newSetFromMap(new ConcurrentHashMap<>()));

    final List<String> newSamples =
        collector.collect().stream().map(metricFamilySamples -> metricFamilySamples.name).toList();

    categoryCollectors.stream()
        .filter(
            c ->
                c.collect().stream()
                    .anyMatch(metricFamilySamples -> newSamples.contains(metricFamilySamples.name)))
        .findFirst()
        .ifPresent(
            c -> {
              categoryCollectors.remove(c);
              registry.unregister(c);
            });

    categoryCollectors.add(collector.register(registry));
  }

  @Override
  public Stream<Observation> streamObservations(final MetricCategory category) {
    return collectors.getOrDefault(category, Collections.emptySet()).stream()
        .flatMap(collector -> collector.collect().stream())
        .flatMap(familySamples -> convertSamplesToObservations(category, familySamples));
  }

  @Override
  public Stream<Observation> streamObservations() {
    return collectors.keySet().stream().flatMap(this::streamObservations);
  }

  @Override
  public void shutdown() {
    registry.clear();
    collectors.clear();
    cachedCounters.clear();
    cachedTimers.clear();
    guavaCacheCollectors.clear();
    guavaCacheNames.clear();
  }

  private Stream<Observation> convertSamplesToObservations(
      final MetricCategory category, final MetricFamilySamples familySamples) {
    return familySamples.samples.stream()
        .map(sample -> createObservationFromSample(category, sample, familySamples));
  }

  private Observation createObservationFromSample(
      final MetricCategory category, final Sample sample, final MetricFamilySamples familySamples) {
    if (familySamples.type == Collector.Type.HISTOGRAM) {
      return convertHistogramSampleNamesToLabels(category, sample, familySamples);
    }
    if (familySamples.type == Collector.Type.SUMMARY) {
      return convertSummarySampleNamesToLabels(category, sample, familySamples);
    }
    if (familySamples.type == Collector.Type.COUNTER) {
      return convertCounterNamesToLabels(category, sample, familySamples);
    }
    return new Observation(
        category,
        convertFromPrometheusName(category, sample.name),
        sample.value,
        sample.labelValues);
  }

  private Observation convertCounterNamesToLabels(
      final MetricCategory category, final Sample sample, final MetricFamilySamples familySamples) {
    final List<String> labelValues = new ArrayList<>(sample.labelValues);
    if (sample.name.endsWith("_created")) {
      labelValues.add("created");
    }

    return new Observation(
        category,
        convertFromPrometheusCounterName(category, familySamples.name),
        sample.value,
        labelValues);
  }

  private Observation convertHistogramSampleNamesToLabels(
      final MetricCategory category, final Sample sample, final MetricFamilySamples familySamples) {
    final List<String> labelValues = new ArrayList<>(sample.labelValues);
    if (sample.name.endsWith("_bucket")) {
      labelValues.add(labelValues.size() - 1, "bucket");
    } else {
      labelValues.add(sample.name.substring(sample.name.lastIndexOf("_") + 1));
    }
    return new Observation(
        category,
        convertFromPrometheusName(category, familySamples.name),
        sample.value,
        labelValues);
  }

  private Observation convertSummarySampleNamesToLabels(
      final MetricCategory category, final Sample sample, final MetricFamilySamples familySamples) {
    final List<String> labelValues = new ArrayList<>(sample.labelValues);
    if (sample.name.endsWith("_sum")) {
      labelValues.add("sum");
    } else if (sample.name.endsWith("_count")) {
      labelValues.add("count");
    } else if (sample.name.endsWith("_created")) {
      labelValues.add("created");
    } else {
      labelValues.add(labelValues.size() - 1, "quantile");
    }
    return new Observation(
        category,
        convertFromPrometheusName(category, familySamples.name),
        sample.value,
        labelValues);
  }

  /**
   * Convert to prometheus name.
   *
   * @param category the category
   * @param name the name
   * @return the name as string
   */
  public String convertToPrometheusName(final MetricCategory category, final String name) {
    return prometheusPrefix(category) + name;
  }

  /**
   * Convert to prometheus counter name. Prometheus adds a _total suffix to the name if not present,
   * so we remember if the original name already has it, to be able to convert back correctly
   *
   * @param category the category
   * @param name the name
   * @return the name as string
   */
  public String convertToPrometheusCounterName(final MetricCategory category, final String name) {
    if (name.endsWith("_total")) {
      totalSuffixedCounters.add(name);
    }
    return convertToPrometheusName(category, name);
  }

  private String convertFromPrometheusName(final MetricCategory category, final String metricName) {
    final String prefix = prometheusPrefix(category);
    return metricName.startsWith(prefix) ? metricName.substring(prefix.length()) : metricName;
  }

  private String convertFromPrometheusCounterName(
      final MetricCategory category, final String metricName) {
    final String unPrefixedName = convertFromPrometheusName(category, metricName);
    return totalSuffixedCounters.contains(unPrefixedName + "_total")
        ? unPrefixedName + "_total"
        : unPrefixedName;
  }

  private String prometheusPrefix(final MetricCategory category) {
    return category.getApplicationPrefix().orElse("") + category.getName() + "_";
  }

  /**
   * Gets registry.
   *
   * @return the registry
   */
  CollectorRegistry getRegistry() {
    return registry;
  }
}
