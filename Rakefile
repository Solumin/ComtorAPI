require 'rake/clean'

#Configure the default Clean and Clobber tasks
CLEAN.include('src/*.class')
CLOBBER.include('src/*.class')
CLOBBER.include('**/ComtorDocs.jar')

ANTLR = 'lib/antlr-3.3.jar'

#Grab all the java source files
JAVA = FileList['src/*.java'].exclude(/src\/(COMTOR*|Test*)/)
#Make a list of java class files
CLASS = JAVA.ext('class')

rule '.class' => '.java' do |t|
	sh "javac -cp src;lib/antlr-3.3.jar;lib/cantlr.jar #{t.source}"
end

file "ComtorDocs.jar" => CLASS do
	cd 'src'
	sh "jar cf ../ComtorDocs.jar *.class"
	cd '..'
end

task :install => "ComtorDocs.jar" do
	sh 'copy ComtorDocs.jar lib\ComtorDocs.jar'
	sh 'copy ComtorDocs.jar ..\ComtorEngine\lib\ComtorDocs.jar'
end

desc "Compile, Jar, clean, and install ComtorDocs"
task :default => [:install, :clean]