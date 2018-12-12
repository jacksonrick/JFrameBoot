package com.jf.poi;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xujunfei
 * Date: 2018-12-11
 * Time: 18:47
 */
public abstract class AbstractCellRender<T> {

    public abstract String render(T t);

    public abstract static class None extends AbstractCellRender<Object> {
        public None() {
        }
    }

}