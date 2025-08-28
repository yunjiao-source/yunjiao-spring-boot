cd ..

call mvn clean install

echo [starter-parent]
cd starter-parent
call mvn clean install

echo [examples]
cd ..
cd examples
call mvn clean install

echo [projects]
cd ..
cd projects
call mvn clean install

rem 切换回运行目录
cd..
cd bin