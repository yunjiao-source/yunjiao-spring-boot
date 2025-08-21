cd ..

call mvn clean install

echo ################################
echo #  spring-boot-starter-parent  #
echo ################################
cd spring-boot-starter-parent
call mvn clean install

echo ################################
echo #          examples            #
echo ################################
cd ..
cd examples
call mvn clean install

echo ################################
echo #          projects            #
echo ################################
cd ..
cd projects
call mvn clean install

rem 切换回运行目录
cd..
cd bin