def findChanged(build) {
  def nginxChanged = false
  def appChanged = false

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


  stage('Pull Repository') { 
    git 'https://github.com/wondee/finance-app.git'
  }
  stage('Build Maven') {
    mvnHome = tool 'M3'

    def changes = findChanged(currentBuild)

    // Run the maven build only if application changed
    if (changes.app) {
      dir ("application") {
        sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
      }
    } else {
      echo "skipping maven build"
    }
  }
  stage('Build Docker') {
    
    def changes = findChanged(currentBuild)
    if (changes.app) {
      sh "sudo docker build -t wondee/finance-application application"
    } else {
      echo "skipping application docker build"
    }
    
    if (changes.nginx) {
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