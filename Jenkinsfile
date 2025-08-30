pipeline {
    agent any

    tools {
        maven 'Maven-3.8.1'   // Must match Maven name configured in Jenkins
        jdk 'JDK-11'          // Must match JDK name configured in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Cloning repository...'
                git 'https://github.com/aryanacharya2004/BookMyShowAutomation.git'
            }
        }

        stage('Build & Test') {
            steps {
                echo 'Running Maven tests...'
                sh 'mvn clean test'
            }
        }

        stage('Publish JUnit Reports') {
            steps {
                junit 'target/surefire-reports/*.xml'
            }
        }

        stage('Publish Cucumber HTML Report') {
            steps {
                publishHTML([[
                    reportDir: 'reports',
                    reportFiles: 'cucumber.html',
                    reportName: 'Cucumber Report'
                ]])
            }
        }
    }

    post {
        success {
            echo '✅ Build successful!'
        }
        failure {
            echo '❌ Build failed! Please check logs.'
        }
        always {
            cleanWs()
        }
    }
}
