package com.tx.txspringboot.entitys;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 *
 * @author Eric Cui
 * @since  2017/12/6 16:53
 * Desc    Setting | Editor | File and Code Templates
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
public class ExampleEntity extends BaseEntity implements Serializable {
    private int id;
    private String desc;
}
