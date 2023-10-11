package org.hyperledger.besu.ethereum.blockcreation.builder;

import org.apache.tuweni.units.bigints.UInt64;
import org.hyperledger.besu.datatypes.Address;
import org.hyperledger.besu.datatypes.Hash;
import org.hyperledger.besu.plugin.data.Withdrawal;
import org.web3j.abi.datatypes.generated.Uint64;

import java.math.BigInteger;

/**
 * Executable data format for building blocks from payload received from external block builders.
 */
public class ExecutableData {
    Hash parentHash;
    Hash feeRecipient;
    Hash stateRoot;
    Hash receiptsRoot;
    byte[] logsBloom;
    UInt64 prevRandao;
    UInt64 number;
    UInt64 gasLimit;
    UInt64 gasUsed;
    UInt64 timestamp;
    byte[] extraData;
    BigInteger baseFeePerGas;
    Hash blockHash;
    byte[][] transactions;
    Withdrawal[] withdrawals;
    UInt64 blobGasUsed;
    UInt64 excessBlobGas;
}

