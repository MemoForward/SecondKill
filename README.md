## Java高并发秒杀系统API

## How to play
 1. git clone `git@github.com:MemoForward/SecondKill.git`
 2. open IDEA -->  File  -->  New  --> Open 
 3. choose seckill's pom.xml，open it
 4. update the `jdbc properties` in `spring-dao.xml` about your mysql's username and password
 5. deploy the tomcat，and start up
 6. enter in the browser: `http://localhost:8080/seckill/list`
 7. enjoy it 

## Develop environment
IDEA+Maven+SSM框架。  

## 1.相关技术介绍
**MySQL:**1.这里我们采用手写代码创建相关表，掌握这种能力对我们以后的项目二次上线会有很大的帮助；2.SQL技巧；3.事务和行级锁的理解和一些应用。  

**MyBatis:**1.DAO层的设计与开发。2.MyBatis的合理使用，使用Mapper动态代理的方式进行数据库的访问。3.MyBatis和Spring框架的整合:如何高效的去整合MyBatis和Spring框架。  

**Spring:**1.Spring IOC帮我们整合Service以及Service所有的依赖。2.声明式事务。对Spring声明式事务做一些分析以及它的行为分析。  

**Spring MVC:**1.Restful接口设计和使用。Restful现在更多的被应用在一些互联网公司Web层接口的应用上。2.框架运作流程。3.Spring Controller的使用技巧。  

**前端:**1.交互设计。2.bootstrap。3.JQuery。设计到前端的页面代码我们直接拷贝即可，毕竟真正开发中这样一个项目是由产品经理、前端工程师、后端工程师一起完成的。  

**高并发:**
1.高并发点和高并发分析。2.优化思路并实现。（由于本人时间有限，目前还未实现高并发的优化，日后一定补上）  

