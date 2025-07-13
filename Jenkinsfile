pipeline {
  agent any

  environment {
    IMAGE_NAME = "selenium-test"
    DOCKERHUB_USER = "arunyadav12"
    TAG = "${BUILD_NUMBER}" // 👈 Optional: use build number for image versioning
  }

  stages {
    stage('Build Docker Image') {
      steps {
        echo "🛠️ Building Docker image: ${IMAGE_NAME}:${TAG}"
        sh "docker build -t ${IMAGE_NAME}:${TAG} ."
      }
    }

    stage('Push to Docker Hub') {
      steps {
        echo "📤 Pushing image to Docker Hub as ${DOCKERHUB_USER}/${IMAGE_NAME}:${TAG}"
        script {
          docker.withRegistry('', 'docker-hub-credentials') {
            sh "docker tag ${IMAGE_NAME}:${TAG} ${DOCKERHUB_USER}/${IMAGE_NAME}:${TAG}"
            sh "docker push ${DOCKERHUB_USER}/${IMAGE_NAME}:${TAG}"
          }
        }
      }
    }

    stage('Run Selenium Test') {
      steps {
        echo "🧪 Running Selenium tests with container: ${IMAGE_NAME}:${TAG}"
        sh "docker run ${IMAGE_NAME}:${TAG}"
      }
    }

    stage('Archive ExtentReports') {
      when {
        expression {
          fileExists('reports/index.html') // ✅ Adjust if your report path differs
        }
      }
      steps {
        echo "🗃️ Archiving ExtentReports"
        archiveArtifacts artifacts: 'reports/**/*.*', fingerprint: true
      }
    }
  }

  post {
    success {
      echo '✅ Build completed successfully. Selenium tests passed.'
    }
    failure {
      echo '❌ Build failed. Review logs and ExtentReports for details.'
    }
  }
}
