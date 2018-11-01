node {
  def mvnHome

  def nginxChanged = false
  def appChanged = false

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
          } else if (file.path.startsWith("application")) {
            appChanged = true
          }
        }
      }
    }
    build = build.previousBuild
  }
  echo changes

  stage('Preparation') { 

    git 'https://github.com/wondee/finance-app.git'

    mvnHome = tool 'M3'
  }
  stage('Build Maven') {
    // Run the maven build only if application changed
    if (appChanged) {
      dir ("application") {
        sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
      }
    }
  }
  stage('Build Docker') {
  
    if (appChanged) {
      sh "sudo docker build -t wondee/finance-application application"
    } 
    
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