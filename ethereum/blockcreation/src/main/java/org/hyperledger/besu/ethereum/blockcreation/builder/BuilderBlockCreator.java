package org.hyperledger.besu.ethereum.blockcreation.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.hyperledger.besu.datatypes.Address;
import org.hyperledger.besu.datatypes.BLSPublicKey;
import org.hyperledger.besu.datatypes.Wei;
import org.hyperledger.besu.ethereum.ProtocolContext;
import org.hyperledger.besu.ethereum.blockcreation.AbstractBlockCreator;
import org.hyperledger.besu.ethereum.core.Block;
import org.hyperledger.besu.ethereum.core.BlockBody;
import org.hyperledger.besu.ethereum.core.BlockHeader;
import org.hyperledger.besu.ethereum.core.Transaction;
import org.hyperledger.besu.datatypes.Hash;

import org.hyperledger.besu.ethereum.eth.transactions.TransactionPool;
import org.hyperledger.besu.ethereum.mainnet.ProtocolSchedule;
import org.web3j.protocol.core.methods.response.EthBlock;


import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class BuilderBlockCreator extends AbstractBlockCreator {

    protected BuilderApi api;

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
            final BuilderApi builderApi
    ) {
        super(coinbase, miningBeneficiaryCalculator, targetGasLimitSupplier, extraDataCalculator, transactionPool,
                protocolContext, protocolSchedule, minTransactionGasPrice, minBlockOccupancyRatio, parentHeader,
                depositContractAddress);
        this.api = builderApi;
    }

    public Optional<BlockCreationResult> fetchBlock() {
        try {
            BlockBody blockBody = this.api.fetchBlockBody(
                    this.parentHeader.getNumber() + 1, this.parentHeader.getHash());
            long timestamp = System.currentTimeMillis();
            BlockCreationResult result = createBlock(
                    Optional.of(blockBody.getTransactions()), Optional.empty(), timestamp);
            return Optional.of(result);
        } catch (IOException e) {
            return Optional.empty();
        }
    }
}
