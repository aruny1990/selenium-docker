pipeline {
    agent any

    environment {
        IMAGE_NAME = "selenium-test"
        DOCKERHUB_USER = "arunyadav12"
        TAG = "latest"
    }

    stages {

        stage('Checkout Code') {
            steps {
                checkout([
                    $class: 'GitSCM',
                    branches: [[name: '*/main']],
                    userRemoteConfigs: [[
                        url: 'https://github.com/arunyadav12/selenium-docker.git',
                        credentialsId: 'github-token'
                    ]]
                ])
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME} ."
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('', 'docker-hub-credentials') {
                        sh "docker tag ${IMAGE_NAME} ${DOCKERHUB_USER}/${IMAGE_NAME}:${TAG}"
                        sh "docker push ${DOCKERHUB_USER}/${IMAGE_NAME}:${TAG}"
                    }
                }
            }
        }

        stage('Run Selenium Test') {
            steps {
                sh "docker run ${IMAGE_NAME}"
            }
        }

        stage('Archive ExtentReports') {
            when {
                expression {
                    fileExists('reports/index.html') // Update to your actual report path
                }
            }
            steps {
                archiveArtifacts artifacts: 'reports/**/*.*', fingerprint: true
            }
        }
    }

    post {
        success {
            echo '✅ Selenium test pipeline completed successfully.'
        }
        failure {
            echo '❌ Build failed — check logs and ExtentReports for details.'
        }
    }
}
