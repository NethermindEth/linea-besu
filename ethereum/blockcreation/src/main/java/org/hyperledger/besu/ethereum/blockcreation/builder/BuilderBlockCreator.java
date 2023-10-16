package org.hyperledger.besu.ethereum.blockcreation.builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hyperledger.besu.datatypes.Address;
import org.hyperledger.besu.datatypes.BLSPublicKey;
import org.hyperledger.besu.datatypes.Wei;
import org.hyperledger.besu.ethereum.ProtocolContext;
import org.hyperledger.besu.ethereum.blockcreation.AbstractBlockCreator;
import org.hyperledger.besu.ethereum.core.Block;
import org.hyperledger.besu.ethereum.core.BlockHeader;
import org.hyperledger.besu.ethereum.core.Transaction;
import org.hyperledger.besu.datatypes.Hash;

import org.hyperledger.besu.ethereum.eth.transactions.TransactionPool;
import org.hyperledger.besu.ethereum.mainnet.ProtocolSchedule;


import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Optional;
import java.util.function.Supplier;

public abstract class BuilderBlockCreator extends AbstractBlockCreator {

    protected BuilderApi api;
//    private BLSPublicKey publicKey;
//
//    private final AtomicBoolean isCancelled = new AtomicBoolean(false);

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


    @Override
    public BlockCreationResult createBlock(final long timestamp) {
        return createBlock(Optional.empty(), Optional.empty(), timestamp);
    }

    @Override
    public BlockCreationResult createBlock(
            final List<Transaction> transactions, final List<BlockHeader> ommers, final long timestamp) {
        return createBlock(Optional.of(transactions), Optional.of(ommers), timestamp);
    }

    @Override
    public BlockCreationResult createBlock(
            final Optional<List<Transaction>> maybeTransactions,
            final Optional<List<BlockHeader>> maybeOmmers,
            final long timestamp) {
        return createBlock(
                maybeTransactions,
                maybeOmmers,
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                timestamp,
                true);
    }
}
