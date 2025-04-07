pipeline {
    agent any

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
                bat 'mvn clean install -DskipTests'
            }
        }

        stage('Test') {
            steps {
                // Run unit tests and generate code coverage report
                bat 'mvn test'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'sonar-token-jen', variable: 'SONAR_TOKEN_JEN')]) {
                        withSonarQubeEnv('SonarQube') {
                            bat "mvnw.cmd clean org.sonarsource.scanner.maven:sonar-maven-plugin:5.1.0.4751:sonar -Dsonar.host.url=http://localhost:9000 -Dsonar.token=\"%SONAR_TOKEN_JEN%\" -Dsonar.java.binaries=target/BOOT-INF/classes -X"
                        }
                    }
                }
            }
        }

        /*
        stage('Deploy') {
            steps {
                script {
                    // Running the Ansible playbook to deploy the Docker container
                    bat 'ansible-playbook -i hosts.ini deploy-docker.yml' // Or use 'wsl' if you're using WSL
                }
            }
        }
        */
    } // <-- Proper closing for 'stages'

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
