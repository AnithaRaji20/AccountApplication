pipeline {
    agent any

    environment {
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_TOKEN = credentials('sonar-token')  // Jenkins credentials for SonarQube token
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from SCM (GitHub)
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Run Maven build command, skipping tests
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                // Run unit tests and generate code coverage report
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    // Ensure SonarQube environment is correctly injected and triggered.
                    withSonarQubeEnv('SonarQube') { // 'SonarQube' is the name you set in Jenkins' SonarQube configuration
                        sh "mvn sonar:sonar -Dsonar.projectKey=com.tus.accounts -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=$SONAR_TOKEN"
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                // Add your deployment logic here (Docker, Kubernetes, etc.)
                echo "Deploying application"
            }
        }
    }

    post {
        always {
            // Clean up steps (e.g., remove temporary files, close connections, etc.)
            echo "Cleaning up"
        }

        success {
            // Actions on successful build (e.g., send success notifications)
            echo "Build succeeded"
        }

        failure {
            // Actions on failure (e.g., send failure notifications)
            echo "Build failed"
        }
    }
}
