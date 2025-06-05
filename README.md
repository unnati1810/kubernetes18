# kubernetes18
Here's a tailored **project summary for your GitHub README.md** file:

---

## 🚀 Microservices Deployment on GCP with CI/CD & Shared Persistent Volume

This project showcases the development and deployment of two simple microservices that interact with each other, containerized and orchestrated using **Google Kubernetes Engine (GKE)**.

### 🛠️ Tech Stack

* **Microservices**: Developed using \[Your Preferred Language]
* **Containerization**: Docker
* **Orchestration**: Kubernetes (GKE)
* **CI/CD**: Cloud Build + Cloud Source Repositories
* **Artifact Storage**: GCP Artifact Registry
* **Persistent Storage**: Kubernetes Persistent Volume

### 🔄 Project Workflow

1. **Microservices Development**: Built two independent yet interacting services.
2. **Containerization**: Dockerized both services.
3. **CI/CD Pipeline**:

   * Source code hosted on **Cloud Source Repositories**.
   * Images built and pushed to **Artifact Registry** via Cloud Build.
   * Automatic deployment to **GKE** upon commits.
4. **Shared Persistent Volume**:

   * Configured a Kubernetes **Persistent Volume (PV)**.
   * Both microservices read/write file data to this shared volume for state persistence.

### 📦 Features

* Inter-service communication via HTTP or gRPC.
* Automated deployment pipeline using native GCP tools.
* Secure, scalable, and production-grade GKE setup.
* Persistent shared data storage between services.

---


