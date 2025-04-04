pipeline {
    agent any
    
    environment {
		SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_TOKEN = credentials('sonar-token')  // Jenkins credentials for SonarQube token
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                
            }
        }

        stage('Build') {
            steps {
                // Run Maven build command
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                // Run tests and generate code coverage report
                sh 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Run SonarQube analysis (assumes SonarQube plugin is installed in Jenkins)
                sh "mvn sonar:sonar -Dsonar.projectKey=com.tus.accounts -Dsonar.host.url=http://localhost:9000 -Dsonar.login=$SONAR_TOKEN"
            }
        }

        stage('Deploy') {
            steps {
                // Deploy the application (could be to Docker, Kubernetes, or any other environment)
                echo "Deploying application"
            }
        }
    }

    post {
        always {
            // Clean up steps (if any)
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
