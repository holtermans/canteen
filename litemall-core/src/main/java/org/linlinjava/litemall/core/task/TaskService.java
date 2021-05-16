//package org.linlinjava.litemall.core.task;
//
//import org.springframework.stereotype.Component;
//import javax.annotation.PostConstruct;
//import java.util.concurrent.DelayQueue;
//import java.util.concurrent.Executors;
//
//@Component
//public class TaskService {
//    private TaskService taskService;
//    private DelayQueue<Task> delayQueue =  new DelayQueue<Task>();
// //DelayQueue是一个无界的BlockingQueue，用于放置实现了Delayed接口的对象，其中的对象只能在其到期时才能从队列中取走。
// //这种队列是有序的，即队头对象的延迟到期时间最长。注意：不能将null元素放置到这种队列中。

//  //构造方法 > @Autowired > @PostConstruct
//    @PostConstruct
//    private void init() {
//        taskService = this;
//
//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    try {
//                        Task task = delayQueue.take();
//                        task.run();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//    }
//
//    public void addTask(Task task){
//        if(delayQueue.contains(task)){
//            return;
//        }
//        delayQueue.add(task);
//    }
//
//    public void removeTask(Task task){
//        delayQueue.remove(task);
//    }
//
//}
