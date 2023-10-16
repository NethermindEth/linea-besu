package org.hyperledger.besu.ethereum.blockcreation.builder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.tuweni.bytes.Bytes;
import org.apache.tuweni.units.bigints.UInt64;
import org.hyperledger.besu.datatypes.Address;
import org.hyperledger.besu.datatypes.Hash;

import java.math.BigInteger;

/**
 * Executable data format for building blocks from payload received from external block builders.
 */
@JsonDeserialize(using = ExecutableDataDeserializer.class)
public class ExecutableData {
    Hash parentHash;
    Address feeRecipient;
    Hash stateRoot;
    Hash receiptsRoot;
    Bytes logsBloom;
    Hash prevRandao;
    UInt64 number;
    UInt64 gasLimit;
    UInt64 gasUsed;
    UInt64 timestamp;
    Bytes extraData;
    BigInteger baseFeePerGas;
    Hash blockHash;
    Bytes[] transactions;
}

