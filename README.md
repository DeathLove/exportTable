# exportTable
用来导出mysql数据库文档，暂时只支持mysql

在info.properties文件中对数据库进行配置；
在testDoc.xml中对word文档模板进行配置：主要用到<#list></#list>来进行循环，通过map的方式进行传输数据；
获取表结构、字段属性时暂时只针对mysql，其他类型的数据库暂时不支持，可以对其进行补充
