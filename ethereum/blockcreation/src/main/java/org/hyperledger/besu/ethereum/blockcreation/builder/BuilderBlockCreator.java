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
package org.hyperledger.besu.ethereum.blockcreation.builder;

import org.hyperledger.besu.datatypes.Address;
import org.hyperledger.besu.datatypes.Wei;
import org.hyperledger.besu.ethereum.ProtocolContext;
import org.hyperledger.besu.ethereum.blockcreation.AbstractBlockCreator;
import org.hyperledger.besu.ethereum.core.BlockBody;
import org.hyperledger.besu.ethereum.core.BlockHeader;
import org.hyperledger.besu.ethereum.eth.transactions.TransactionPool;
import org.hyperledger.besu.ethereum.mainnet.ProtocolSchedule;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class BuilderBlockCreator extends AbstractBlockCreator {

  protected BuilderClient api;

  public BuilderBlockCreator(
      final Address coinbase,
      final MiningBeneficiaryCalculator miningBeneficiaryCalculator,
      final Supplier<Optional<Long>> targetGasLimitSupplier,
      final ExtraDataCalculator extraDataCalculator,
      final TransactionPool transactionPool,
      final ProtocolContext protocolContext,
      final ProtocolSchedule protocolSchedule,
      final Wei minTransactionGasPrice,
      final Double minBlockOccupancyRatio,
      final BlockHeader parentHeader,
      final Optional<Address> depositContractAddress,
      final BuilderClient builderClient) {
    super(
        coinbase,
        miningBeneficiaryCalculator,
        targetGasLimitSupplier,
        extraDataCalculator,
        transactionPool,
        protocolContext,
        protocolSchedule,
        minTransactionGasPrice,
        minBlockOccupancyRatio,
        parentHeader,
        depositContractAddress);
    this.api = builderClient;
  }

  public Optional<BlockCreationResult> fetchBlock() {
    try {
      BlockBody blockBody =
          this.api.fetchBlockBody(this.parentHeader.getNumber() + 1, this.parentHeader.getHash());
      long timestamp = System.currentTimeMillis();
      BlockCreationResult result =
          createBlock(Optional.of(blockBody.getTransactions()), Optional.empty(), timestamp);
      return Optional.of(result);
    } catch (IOException e) {
      return Optional.empty();
    }
  }
}
