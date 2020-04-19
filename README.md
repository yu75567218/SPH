# SPH
试用apk包下载地址 [http://d.6short.com/6jxc](http://d.6short.com/6jxc)<br>
显示2008年至2018年通过新加坡移动网络发送的数据量。<br>
本应用程序主题采用MVVM架构，即Model、View、ViewModel。View层做的就是和UI相关的工作，不做和业务相关的事。ViewModel层做的事情刚好和View层相反，ViewModel 只做和业务逻辑和业务数据相关的事，不做任何和UI、控件相关的事。Model实体模型。<br>
View中采用databinding实现数据自动绑定。<br>
Model采用网络+数据库缓存双数据模型，优先从本地获取缓存的数据显示到UI上，然后再从网络上拉取最新数据，格式化后保存到本地数据库，通过数据驱动自动实现UI数据更新。<br>
ViewModel从Model层中国获取到数据，然后更新到ViewGroup层中，实现了数据格式转换到业务逻辑。配合RxJava+room+retrofit轻松实现数据链式操作。<br>
对网络模块、数据库缓存和ViewModel做了相关的单元测试。<br>