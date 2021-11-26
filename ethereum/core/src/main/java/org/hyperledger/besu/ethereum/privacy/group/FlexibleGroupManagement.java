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
package org.hyperledger.besu.ethereum.privacy.group;

import org.apache.tuweni.bytes.Bytes;

public class FlexibleGroupManagement {
  public static final Bytes PROXY_RUNTIME_BYTECODE =
      Bytes.fromHexString(
          "608060405234801561001057600080fd5b506004361061009e5760003560e01c80639738968c116100665780639738968c146101b8578063a69df4b5146101d8578063b4926e25146101e2578063f83d08ba146102b0578063fd017797146102ba5761009e565b80630d8e6e2c146100a35780633659cfe6146100c15780635aa68ac0146101055780635c60da1b1461016457806378b9033714610198575b600080fd5b6100ab6102fe565b6040518082815260200191505060405180910390f35b610103600480360360208110156100d757600080fd5b81019080803573ffffffffffffffffffffffffffffffffffffffff1690602001909291905050506103ab565b005b61010d61083e565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b83811015610150578082015181840152602081019050610135565b505050509050019250505060405180910390f35b61016c610987565b604051808273ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b6101a06109ab565b60405180821515815260200191505060405180910390f35b6101c0610a58565b60405180821515815260200191505060405180910390f35b6101e0610b07565b005b610298600480360360208110156101f857600080fd5b810190808035906020019064010000000081111561021557600080fd5b82018360208201111561022757600080fd5b8035906020019184602083028401116401000000008311171561024957600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f820116905080830192505050505050509192919290505050610b90565b60405180821515815260200191505060405180910390f35b6102b8610c8d565b005b6102e6600480360360208110156102d057600080fd5b8101908080359060200190929190505050610d16565b60405180821515815260200191505060405180910390f35b60008060008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff16630d8e6e2c6040518163ffffffff1660e01b815260040160206040518083038186803b15801561036a57600080fd5b505afa15801561037e573d6000803e3d6000fd5b505050506040513d602081101561039457600080fd5b810190808051906020019092919050505091505090565b3073ffffffffffffffffffffffffffffffffffffffff166378b903376040518163ffffffff1660e01b815260040160206040518083038186803b1580156103f157600080fd5b505afa158015610405573d6000803e3d6000fd5b505050506040513d602081101561041b57600080fd5b810190808051906020019092919050505061049e576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260178152602001807f54686520636f6e7472616374206973206c6f636b65642e00000000000000000081525060200191505060405180910390fd5b8073ffffffffffffffffffffffffffffffffffffffff1660008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff161415610543576040517f08c379a0000000000000000000000000000000000000000000000000000000008152600401808060200182810382526054815260200180610e5a6054913960600191505060405180910390fd5b3073ffffffffffffffffffffffffffffffffffffffff16639738968c6040518163ffffffff1660e01b8152600401602060405180830381600087803b15801561058b57600080fd5b505af115801561059f573d6000803e3d6000fd5b505050506040513d60208110156105b557600080fd5b810190808051906020019092919050505061061b576040517f08c379a000000000000000000000000000000000000000000000000000000000815260040180806020018281038252602f815260200180610eae602f913960400191505060405180910390fd5b60603073ffffffffffffffffffffffffffffffffffffffff16635aa68ac06040518163ffffffff1660e01b815260040160006040518083038186803b15801561066357600080fd5b505afa158015610677573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f8201168201806040525060208110156106a157600080fd5b81019080805160405193929190846401000000008211156106c157600080fd5b838201915060208201858111156106d757600080fd5b82518660208202830111640100000000821117156106f457600080fd5b8083526020830192505050908051906020019060200280838360005b8381101561072b578082015181840152602081019050610710565b50505050905001604052505050905061074382610e16565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff1663b4926e25836040518263ffffffff1660e01b81526004018080602001828103825283818151815260200191508051906020019060200280838360005b838110156107d85780820151818401526020810190506107bd565b5050505090500192505050602060405180830381600087803b1580156107fd57600080fd5b505af1158015610811573d6000803e3d6000fd5b505050506040513d602081101561082757600080fd5b810190808051906020019092919050505050505050565b606060008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff16635aa68ac06040518163ffffffff1660e01b815260040160006040518083038186803b1580156108ac57600080fd5b505afa1580156108c0573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f8201168201806040525060208110156108ea57600080fd5b810190808051604051939291908464010000000082111561090a57600080fd5b8382019150602082018581111561092057600080fd5b825186602082028301116401000000008211171561093d57600080fd5b8083526020830192505050908051906020019060200280838360005b83811015610974578082015181840152602081019050610959565b5050505090500160405250505091505090565b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b60008060008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff166378b903376040518163ffffffff1660e01b815260040160206040518083038186803b158015610a1757600080fd5b505afa158015610a2b573d6000803e3d6000fd5b505050506040513d6020811015610a4157600080fd5b810190808051906020019092919050505091505090565b60008060008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff16639738968c6040518163ffffffff1660e01b8152600401602060405180830381600087803b158015610ac657600080fd5b505af1158015610ada573d6000803e3d6000fd5b505050506040513d6020811015610af057600080fd5b810190808051906020019092919050505091505090565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff1663a69df4b56040518163ffffffff1660e01b8152600401600060405180830381600087803b158015610b7557600080fd5b505af1158015610b89573d6000803e3d6000fd5b5050505050565b60008060008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff1663b4926e25846040518263ffffffff1660e01b81526004018080602001828103825283818151815260200191508051906020019060200280838360005b83811015610c25578082015181840152602081019050610c0a565b5050505090500192505050602060405180830381600087803b158015610c4a57600080fd5b505af1158015610c5e573d6000803e3d6000fd5b505050506040513d6020811015610c7457600080fd5b8101908080519060200190929190505050915050919050565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1690508073ffffffffffffffffffffffffffffffffffffffff1663f83d08ba6040518163ffffffff1660e01b8152600401600060405180830381600087803b158015610cfb57600080fd5b505af1158015610d0f573d6000803e3d6000fd5b5050505050565b60008060008054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905060008173ffffffffffffffffffffffffffffffffffffffff1663fd017797856040518263ffffffff1660e01b815260040180828152602001915050602060405180830381600087803b158015610d9157600080fd5b505af1158015610da5573d6000803e3d6000fd5b505050506040513d6020811015610dbb57600080fd5b810190808051906020019092919050505090508015610e0c577fef2df0cc0f44b5a36a7de9951ef49ba4d861649244ff89bcf7ffaa1ac7291e89846040518082815260200191505060405180910390a15b8092505050919050565b806000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505056fe54686520636f6e747261637420746f207570677261646520746f2068617320746f20626520646966666572656e742066726f6d207468652063757272656e74206d616e6167656d656e7420636f6e74726163742e4e6f7420616c6c6f77656420746f207570677261646520746865206d616e6167656d656e7420636f6e74726163742ea26469706673582212203cacc7680fcd907639c87ed995ee91e1de9ef4d7da7437f854fb3a8fbab9b0db64736f6c634300060c0033");

  public static final Bytes DEFAULT_GROUP_MANAGEMENT_RUNTIME_BYTECODE =
      Bytes.fromHexString(
          "608060405234801561001057600080fd5b50600436106100885760003560e01c8063a69df4b51161005b578063a69df4b51461014a578063b4926e2514610154578063f83d08ba14610222578063fd0177971461022c57610088565b80630d8e6e2c1461008d5780635aa68ac0146100ab57806378b903371461010a5780639738968c1461012a575b600080fd5b610095610270565b6040518082815260200191505060405180910390f35b6100b361027a565b6040518080602001828103825283818151815260200191508051906020019060200280838360005b838110156100f65780820151818401526020810190506100db565b505050509050019250505060405180910390f35b6101126102d2565b60405180821515815260200191505060405180910390f35b6101326102e8565b60405180821515815260200191505060405180910390f35b61015261033f565b005b61020a6004803603602081101561016a57600080fd5b810190808035906020019064010000000081111561018757600080fd5b82018360208201111561019957600080fd5b803590602001918460208302840111640100000000831117156101bb57600080fd5b919080806020026020016040519081016040528093929190818152602001838360200280828437600081840152601f19601f820116905080830192505050505050509192919290505050610437565b60405180821515815260200191505060405180910390f35b61022a6105e3565b005b6102586004803603602081101561024257600080fd5b81019080803590602001909291905050506106d9565b60405180821515815260200191505060405180910390f35b6000600154905090565b606060028054806020026020016040519081016040528092919081815260200182805480156102c857602002820191906000526020600020905b8154815260200190600101908083116102b4575b5050505050905090565b60008060149054906101000a900460ff16905090565b60008060009054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff1614905090565b600060149054906101000a900460ff161561035957600080fd5b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff161461041a576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260158152602001807f4f726967696e206e6f7420746865206f776e65722e000000000000000000000081525060200191505060405180910390fd5b6001600060146101000a81548160ff021916908315150217905550565b60008060149054906101000a900460ff161561045257600080fd5b600073ffffffffffffffffffffffffffffffffffffffff1660008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1614156104e857326000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff16146105a9576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260158152602001807f4f726967696e206e6f7420746865206f776e65722e000000000000000000000081525060200191505060405180910390fd5b60006105b4836107d3565b90506001600060146101000a81548160ff0219169083151502179055506105d9610a0c565b5080915050919050565b600060149054906101000a900460ff166105fc57600080fd5b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff16146106bd576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260158152602001807f4f726967696e206e6f7420746865206f776e65722e000000000000000000000081525060200191505060405180910390fd5b60008060146101000a81548160ff021916908315150217905550565b60008060149054906101000a900460ff166106f357600080fd5b60008054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff16146107b4576040517f08c379a00000000000000000000000000000000000000000000000000000000081526004018080602001828103825260158152602001807f4f726967696e206e6f7420746865206f776e65722e000000000000000000000081525060200191505060405180910390fd5b60006107bf83610a96565b90506107c9610a0c565b5080915050919050565b6000806001905060005b8351811015610a02576108028482815181106107f557fe5b6020026020010151610b81565b156108a7577fcc7365305ae5f16c463d1383713d699f43c5548bbda5537ee61373ceb9aaf213600085838151811061083657fe5b6020026020010151604051808315158152602001828152602001806020018281038252601b8152602001807f4163636f756e7420697320616c72656164792061204d656d6265720000000000815250602001935050505060405180910390a18180156108a0575060005b91506109f5565b60006108c58583815181106108b857fe5b6020026020010151610ba1565b9050606081610909576040518060400160405280601b81526020017f4163636f756e7420697320616c72656164792061204d656d6265720000000000815250610923565b604051806060016040528060218152602001610c18602191395b90507fcc7365305ae5f16c463d1383713d699f43c5548bbda5537ee61373ceb9aaf2138287858151811061095357fe5b60200260200101518360405180841515815260200183815260200180602001828103825283818151815260200191508051906020019080838360005b838110156109aa57808201518184015260208101905061098f565b50505050905090810190601f1680156109d75780820380516001836020036101000a031916815260200191505b5094505050505060405180910390a18380156109f05750815b935050505b80806001019150506107dd565b5080915050919050565b60006001430340416002604051602001808481526020018373ffffffffffffffffffffffffffffffffffffffff1660601b81526014018280548015610a7057602002820191906000526020600020905b815481526020019060010190808311610a5c575b505093505050506040516020818303038152906040528051906020012060018190555090565b60008060036000848152602001908152602001600020549050600081118015610ac457506002805490508111155b15610b76576002805490508114610b32576000600260016002805490500381548110610aec57fe5b906000526020600020015490508060026001840381548110610b0a57fe5b9060005260206000200181905550816003600083815260200190815260200160002081905550505b6002805480610b3d57fe5b60019003818190600052602060002001600090559055600060036000858152602001908152602001600020819055506001915050610b7c565b60009150505b919050565b600080600360008481526020019081526020016000205414159050919050565b60008060036000848152602001908152602001600020541415610c0d576002829080600181540180825580915050600190039060005260206000200160009091909190915055600280549050600360008481526020019081526020016000208190555060019050610c12565b600090505b91905056fe4d656d626572206163636f756e74206164646564207375636365737366756c6c79a26469706673582212208c78668851cd6fd2c8c66c0f57ff99c07d7fa22aaac6e0806def9d467f320ecf64736f6c634300060c0033");

  public static final Bytes ADD_PARTICIPANTS_METHOD_SIGNATURE = Bytes.fromHexString("0xb4926e25");
  public static final Bytes CAN_EXECUTE_METHOD_SIGNATURE = Bytes.fromHexString("0x78b90337");
  public static final Bytes GET_PARTICIPANTS_METHOD_SIGNATURE = Bytes.fromHexString("0x5aa68ac0");
  public static final Bytes GET_VERSION_METHOD_SIGNATURE = Bytes.fromHexString("0x0d8e6e2c");
  public static final Bytes LOCK_GROUP_METHOD_SIGNATURE = Bytes.fromHexString("0xf83d08ba");
  public static final Bytes REMOVE_PARTICIPANT_METHOD_SIGNATURE = Bytes.fromHexString("0xfd017797");
  public static final Bytes UNLOCK_GROUP_METHOD_SIGNATURE = Bytes.fromHexString("0xa69df4b5");
}