package com.log.blog.vo;

public class View {
    public interface Base { }

    public interface Owner extends Base { }
    public interface Admin extends Base { }
    public interface Guest extends Base { }

    public interface Summary extends Base { }
    public interface Details extends Base { }
}
