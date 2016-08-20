多线程演示项目
------------

本项目包含了3个程序,分别演示了:
* `com.dewafer.threading.demo.RootReduceProcessor`: 任务列表线程递归切块分配处理(仿Java8 Spliterator + fork/join framework实现的parallelStream API)
* `com.dewafer.threading.demo.SingleProducerMultiConsumerDemo`: 生产-消费模式,单线程生产者,多线程处理者
* `com.dewafer.threading.demo.SingleProducerSingleConsumerDemo`: 生产-消费模式,单线程生产者,单线程处理者。

本项目仅依赖Java 6,主要靠`java.util.concurrent.*`包下的类,譬如`BlockingQueue`,`ExecutorService`,`Callable`,`Future`等类实现。


### 主要类说明


#### `com.dewafer.threading.demo.main.ProducerConsumerMain`
程序主入口之一。// TODO 增加详细说明


#### `com.dewafer.threading.demo.main.ReduceProcessorMain`
程序主入口之一。// TODO 增加详细说明


#### `com.dewafer.threading.demo.support.Producer`
生产者接口,生产者的实现必须实现此接口的以下两个方法:
* `T produce()`: 产生被消费者消费的类的对象。
* `boolean hasNext()`: 返回`boolean`表示该生产者是否还能继续生产。


#### `com.dewafer.threading.demo.support.Consumer`
消费者接口,消费者的实现类必须实现此接口的以下两个方法:
* `boolean accept(T t)`: 消费者消费的对象首先会传入此方法,返回`boolean`值表示消费者是否愿意消费此对象,如果返回`false`则该消费者的`consume`方法将不会运行。
* `R consume(T t)`: 消费者具体消费方法的实现接口。仅当`accept`方法返回`true`此方法才会执行。返回消费后产物。


#### `com.dewafer.threading.demo.support.Consumer`
消费者工厂接口,用来生产消费者实现的对象。


#### `com.dewafer.threading.demo.support.Reducer`
消减器接口。与`Consumer`相同,区别仅在`Reducer`没有`accept`方法,返回`null`表示该对象不能被消费。


#### `com.dewafer.threading.demo.impl.StringProducer`
实现了`Producer`接口的字符串生产者,随机产生长度为3到7个字符的纯英文小写字母字符串,生产的个数由`MAX`字段指定。


#### `com.dewafer.threading.demo.impl.ReverseConsumer`
实现了`Consumer`接口的字符串消费者,只接受回文。如`aaa`,`aba`,`abcba`等。消费时(仅`consume`方法中),会随机等待0.2-1.2秒。消费的产物为回文原文。


#### `com.dewafer.threading.demo.impl.ReverseConsumerFactory`
实现了`ConsumerFactory`接口的字符串生产者工厂,将会产生`ReverseConsumer`对象。


#### `com.dewafer.threading.demo.impl.StringReducerImpl`
与`ReverseConsumer`相同,区别仅在于,当消费对象不是回文时将返回`null`(不接受时不等待,消费时则会等待)。


#### `com.dewafer.threading.demo.SingleProducerSingleConsumerDemo`
单线程生产者、单线程消费者演示类。

该类会首先在当前线程(主线程)上启动一个消费者线程(由向`ExecutorService`提交`BlockingQueuedConsumerRunnerWrapper`实现),
然后由主线程担当生产者角色,使用由构造器提供的`Producer`产生产品放入阻塞列队。
当生产者生产完成时,向列队中加入生产终止信号(`END_OF_QUEUE`),
然后等待消费者线程完成并返回消费者消费的结果。

不考虑列队或消费者线程被迫中断的情况,如发生中断或执行列外,将会返回空结果集。


#### `com.dewafer.threading.demo.SingleProducerMultiConsumerDemo`
单线程生产者、多线程消费者演示。

过程与`SingleProducerSingleConsumerDemo`类似,不同之处在于本演示不使用阻塞列队,
而是针对每一个生产者产生的对象,提请启用新线程处理(由向`ExecutorService`提交`NoneBlockingConsumerRunnerWrapper`实现),
并收集每个提请的将来结果(`Future`对象),在生产者生产完成后等待并归集每个提请的结果。

对中断处理的方针与`SingleProducerSingleConsumerDemo`相同,但是如在归集将来结果时发生中断或执行例外,将会返回当前已经收集到的结果集而不是空结果集。


#### `com.dewafer.threading.demo.BlockingQueuedConsumerRunnerWrapper`

// TODO 增加详细说明

#### `com.dewafer.threading.demo.NoneBlockingConsumerRunnerWrapper`

// TODO 增加详细说明

#### `com.dewafer.threading.demo.RootReduceProcessor`

// TODO 增加详细说明

#### `com.dewafer.threading.demo.AbstractListSplittableTask`

// TODO 增加详细说明

#### `com.dewafer.threading.demo.ReduceTaskImpl`

// TODO 增加详细说明


### 辅助类

* `com.dewafer.threading.demo.support.Log`
* `com.dewafer.threading.demo.support.RawProductWrapper`
* `com.dewafer.threading.demo.support.RunResult`
* `com.dewafer.threading.demo.support.TaskResult`
* `com.dewafer.threading.demo.support.Tuple`

// TODO 增加详细说明