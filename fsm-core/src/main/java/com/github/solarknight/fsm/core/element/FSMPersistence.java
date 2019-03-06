package com.github.solarknight.fsm.core.element;

/**
 * @author peiheng.zph created on Mar 06, 2019
 * @version 1.0
 */
@FunctionalInterface
public interface FSMPersistence<T extends FSMMixin> {

  void persist(T t);
}
