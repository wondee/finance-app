def findChanged(build) {
  def nginxChanged = false
  def appChanged = false

  build = currentBuild
  while(build != null && build.result != 'SUCCESS') {
    //changes += "In ${build.id}:\n"
    for (changeLog in build.changeSets) {
      for(entry in changeLog.items) {
        for(file in entry.affectedFiles) {
      

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

  return [nginx: nginxChanged, app: appChanged]
}

node {
  def mvnHome


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
    } else {
      echo "skipping maven build"
    }
  }
  stage('Build Docker') {
  
    if (appChanged) {
      sh "sudo docker build -t wondee/finance-application application"
    } else {
      echo "skipping application docker build"
    }
    
    if (nginxChanged) {
      sh "sudo docker build -t wondee/finance-frontproxy deploy/nginx" 
    } else {
      echo "skipping application docker build"
    }
  }
  stage('Deployment') {
    echo "deploying application"

    dir ("deploy") {
      sh "sudo docker-compose up -d"
    }
  }
}