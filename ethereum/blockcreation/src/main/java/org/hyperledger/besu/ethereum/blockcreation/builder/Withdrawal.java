package org.hyperledger.besu.ethereum.blockcreation.builder;

import org.apache.tuweni.units.bigints.UInt64;
import org.hyperledger.besu.datatypes.Address;

public class Withdrawal {
    UInt64 index;
    UInt64 validator;
    Address address;
    UInt64 amount;
}
