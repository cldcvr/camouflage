package com.cldcvr.camouflage.core.info.types;

import com.cldcvr.camouflage.core.mask.types.AbstractMaskType;
import java.io.Serializable;

/**
 * Abstract behaviour that is used to describe a masking type
 */
public abstract class AbstractInfoType implements Serializable {
     public abstract String name();

    public abstract AbstractMaskType getMaskStrategy();

    public abstract String regex();

    public abstract String algorithm(String input);
}
