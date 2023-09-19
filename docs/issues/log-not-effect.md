# 日志在shade-plugin插件使用后不能成功打包以及加载配置文件
1. 正确配置加载对应的配置文件，避免直接使用默认的配置文件logback.xml(使用一个命名为"confusedLogback.xml")
2. 修改日志的路径及前缀