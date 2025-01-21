import axios from 'axios';

const backendUrl = import.meta.env.VITE_BACKEND_URL;

class API {
  static instance = axios.create({
    baseURL: backendUrl,
    withCredentials: true,
  });

  static getAuthToken() {
    return localStorage.getItem('authToken');
  }

  // Automatically attach the bearer token to each request
  static setAuthHeader() {
    const token = this.getAuthToken();
    if (token) {
      this.instance.defaults.headers['Authorization'] = `Bearer ${token}`;
    } else {
      delete this.instance.defaults.headers['Authorization'];
    }
  }

  // Method to GET data
  static async get(url) {
    this.setAuthHeader();  // Add token before request
    try {
      const response = await this.instance.get(url);
      return response.data;
    } catch (error) {
      throw new Error(`Error fetching data: ${error}`);
    }
  }

  // Method to POST data
  static async post(url, data) {
    this.setAuthHeader();  // Add token before request
    try {
      const response = await this.instance.post(url, data);
      return response.data;
    } catch (error) {
      throw new Error(`Error posting data: ${error}`);
    }
  }

  // Method to PUT data
  static async put(url, data) {
    this.setAuthHeader();  // Add token before request
    try {
      const response = await this.instance.put(url, data);
      return response.data;
    } catch (error) {
      throw new Error(`Error updating data: ${error}`);
    }
  }

  // Method to DELETE data
  static async delete(url) {
    this.setAuthHeader();  // Add token before request
    try {
      const response = await this.instance.delete(url);
      return response.data;
    } catch (error) {
      throw new Error(`Error deleting data: ${error}`);
    }
  }
}

export default API;
