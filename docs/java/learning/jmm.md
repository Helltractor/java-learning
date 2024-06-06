# JMM


## JMM and JVM

JMM 是 Java 虚拟机(JVM)在计算机内存(RAM)中的工作方式，JMM 定义了线程和主内存之间的抽象关系：线程之间的共享变量存储在主内存（Main Memory）中，每个线程都有一个私有的本地内存（Local Memory），本地内存中存储了该线程以读/写共享变量的副本，本地内存是 JMM 的一个抽象概念，并不真实存在。它涵盖了缓存、写缓冲区、寄存器以及其他的硬件和编译器优化。而 JVM 则是描述的是 Java 虚拟机内部及各个结构间的关系。

* JVM 内存结构和 Java 虚拟机的运行时区域相关，定义了 JVM 在运行时如何分区存储程序数据，就比如说堆主要用于存放对象实例。
* Java 内存模型（JMM） 和 Java 的并发编程相关，抽象了线程和主内存之间的关系就比如说线程之间的共享变量必须存储在主内存中，规定了从 Java 源代码到 CPU 可执行指令的这个转化过程要遵守哪些和并发相关的原则和规范，其主要目的是为了简化多线程编程，增强程序可移植性的。

## 缓存不一致问题

解决 JMM 中的本地内存变量的缓存不一致问题有两种解决方案，分别是总线加锁和MESI缓存一致性协议

### MSI protocol (缓存一致性协议)

> MESI 缓存一致性协议是多个 CPU 从主内存读取同一个数据到各自的高速缓存中，当其中的某个 CPU 修改了缓存里的数据，该数据会马上同步回主内存，其它 CPU 通过总线嗅探机制可以感知到数据的变化从而将自己缓存里的数据失效。

在并发编程中，如果多个线程对同一个共享变量进行操作是，我们通常会在变量名称前加上关键在**volatile**,因为它可以保证线程对变量的修改的可见性，保证可见性的基础是多个线程都会监听总线。即当一个线程修改了共享变量后，该变量会立马同步到主内存，其余线程监听到数据变化后会使得自己缓存的原数据失效，并触发read操作读取新修改的变量的值。进而保证了多个线程的数据一致性。事实上，**volatile**的工作原理就是依赖于 MESI 缓存一致性协议实现的。

## happens-before relationship

> happens-before 原则表达的意义其实并不是一个操作发生在另外一个操作的前面，虽然这从程序员的角度上来说也并无大碍。更准确地来说，它更想表达的意义是前一个操作的结果对于后一个操作是可见的，无论这两个操作是否在同一个线程里。

### details (all 8 tips)

* Program Order Rule: Each action in a thread happens-before every action in that thread that comes later in the program order.  
* Monitor Lock Rule: An unlock on a monitor lock happens-before every subsequent lock on that same monitor lock.  
* Volatile Variable Rule: A write to a volatile field happens-before every subsequent read of that same field.  
* Thread Start Rule: A call to Thread.start on a thread happens-before any action in the started thread.  
* Thread Termination Rule: Any action in a thread happens-before any other thread detects that thread has terminated, either by successfully return from Thread.join or by Thread.isAlive returning false.  
* Transitivity: If A happens-before B, and B happens-before C, then A happens-before C.

## summary

* Java 是最早尝试提供内存模型的语言，其主要目的是为了简化多线程编程，增强程序可移植性的。
* CPU 可以通过制定缓存一致协议（比如 MESI 协议）来解决内存缓存不一致性问题。
* 为了提升执行速度/性能，计算机在执行程序代码的时候，会对指令进行重排序。 简单来说就是系统在执行代码的时候并不一定是按照你写的代码的顺序依次执行。指令重排序可以保证串行语义一致，但是没有义务保证多线程间的语义也一致 ，所以在多线程下，指令重排序可能会导致一些问题。
* 你可以把 JMM 看作是 Java 定义的并发编程相关的一组规范，除了抽象了线程和主内存之间的关系之外，其还规定了从 Java 源代码到 CPU 可执行指令的这个转化过程要遵守哪些和并发相关的原则和规范，其主要目的是**为了简化多线程编程，增强程序可移植性的**。
* JSR 133 引入了 happens-before 这个概念来描述两个操作之间的内存可见性。

## reference

* 嘿，同学，你要的 Java 内存模型 (JMM) 来了：https://xie.infoq.cn/article/739920a92d0d27e2053174ef2
* docs/java/concurrent/jmm.md: https://github.com/Snailclimb/JavaGuide/blob/main/docs/java/concurrent/jmm.md