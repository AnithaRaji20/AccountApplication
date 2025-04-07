pipeline {
    agent any

    environment {
        SONARQUBE_URL = "http://localhost:9000"
        SONARQUBE_TOKEN = "squ_263decad84ae28a5542f1167769d1ea84ecd86af"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Code Analysis with SonarQube') {
            steps {
                bat """
                    mvn clean verify sonar:sonar ^
                        -Dsonar.projectKey=accounts ^
                        -Dsonar.host.url=%SONARQUBE_URL% ^
                        -Dsonar.login=%SONARQUBE_TOKEN% ^
                        -Dsonar.java.binaries=target/classes
                """
            }
        }
	

        stage('Deploy with Docker Compose') {
            steps {
                script {
                    echo 'Stopping existing containers...'
                    bat 'docker-compose down'

                    echo 'Pulling latest images...'
                    bat "docker pull ${DOCKER_HUB_USER}/${APP_IMAGE}:latest"

                    echo 'Starting new deployment...'
                    bat 'docker-compose up -d'
                   
                    echo 'Showing docker compose logs'
                    bat 'docker-compose logs'
                }
            }
        }

    }

    post {
        always {
            echo "Cleaning up"
        }
        success {
            echo "Build succeeded"
        }
        failure {
            echo "Build failed"
        }
    }
}
