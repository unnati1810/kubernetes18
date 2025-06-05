provider "google" {
  credentials = file("cedar-chemist-417601-9a26d0b67d2e.json")
  project = "cedar-chemist-417601"
  region  = "us-central1"
}

resource "google_container_cluster" "primary" {
  name               = "gke-cluster"
  location           = "us-central1-a"
  initial_node_count = 1

  node_config {
    machine_type = "e2-medium"
    disk_size_gb = 10
    disk_type    = "pd-standard"
    image_type   = "COS_CONTAINERD"
  }

  master_auth {
    client_certificate_config {
      issue_client_certificate = false
    }
  }
}