cd ..

call mvn clean deploy -Prelease

echo [starter-parent]
cd starter-parent
call mvn clean deploy -Prelease

rem 切换回运行目录
cd..
cd bin