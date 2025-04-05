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
           
					withSonarQubeEnv('SonarQube') { // 'SonarQube' should match the name you set in the Jenkins SonarQube configuration
					// Run the SonarQube scan using Maven
					sh './mvnw clean org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar'
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
