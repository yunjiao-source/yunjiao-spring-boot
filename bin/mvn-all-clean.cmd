cd ..

call mvn clean

echo ################################
echo #  spring-boot-starter-parent  #
echo ################################
cd spring-boot-starter-parent
call mvn clean

echo ################################
echo #          examples            #
echo ################################
cd ..
cd examples
call mvn clean

echo ################################
echo #          projects            #
echo ################################
cd ..
cd projects
call mvn clean

rem 切换回运行目录
cd..
cd bin