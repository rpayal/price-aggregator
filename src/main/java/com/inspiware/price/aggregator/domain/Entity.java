package com.inspiware.price.aggregator.domain;

import java.io.Serializable;

/**
 * Base entity object which extends Serializable marker interface.
 */
public interface Entity extends Serializable {
    boolean equals(Entity otherEntity);

    int hashCode();
}
