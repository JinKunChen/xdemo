package com.topsem.common.domain.view;

/**
 * @author Chen on 14-12-10.
 */
public class View {

    public interface IdOnly {
    }

    public interface NameOnly {
    }

    public interface Public extends NameOnly, IdOnly {
    }

    public interface Checkable extends Public {
    }


    public interface WithParent extends Public {
    }

    public interface WithChildren extends Public {
    }

    public interface CheckableAndWithChildren extends Checkable, WithChildren {
    }


}
