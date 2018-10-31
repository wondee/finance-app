node {
   def mvnHome
   def docker
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git 'https://github.com/wondee/finance-app.git'
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.           
      mvnHome = tool 'M3'
   }
   stage('Build Maven') {
      // Run the maven build
      sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
   }
   stage('Build Docker') {
      
      def nginxChanged = false
      def changes = "Changes:\n"
      build = currentBuild
      while(build != null && build.result != 'SUCCESS') {
        changes += "In ${build.id}:\n"
        for (changeLog in build.changeSets) {
          for(entry in changeLog.items) {
            for(file in entry.affectedFiles) {
              changes += "* ${file.path}\n"

              if (file.path.startsWith("deploy/nginx")) {
                nginxChanged = true
              }
            }
          }
        }
        build = build.previousBuild
      }
      echo changes
    
      sh "sudo docker build -t wondee/finance-application ."
      
      if (nginxChanged) {
        sh "sudo docker build -t wondee/finance-frontproxy deploy/nginx" 
      }
   }
   stage('Deployment') {
      dir ("deploy") {
        sh "sudo docker-compose up -d"
      }
   }
}