CREATE TABLE IF NOT EXISTS accounts (
    account_holder_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number BIGINT NOT NULL,
    account_type VARCHAR(100) NOT NULL,
    branch_address VARCHAR(200) NOT NULL,
    account_holder_name VARCHAR(255) NOT NULL,
    balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    status VARCHAR(50) DEFAULT 'Active'
);