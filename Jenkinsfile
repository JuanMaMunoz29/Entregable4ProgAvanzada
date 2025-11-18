pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                sh './mvnw clean test'
            }
        }

        stage('Package') {
            steps {
                sh './mvnw package -DskipTests'
            }
        }

        stage('Deploy') {
            steps {
                sh 'chmod +x ./deploy.sh || true'
                sh './deploy.sh'
            }
        }
    }

    post {
        always {
            echo 'Pipeline finalizado.'
        }
    }
}
