import axios from "axios";

export default class SendRequestService {
  static async loadAccountsAsync() {
    console.log('service')
    return axios.post("/accounts", {
      "bankId": "mockBank",
      "bearerToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJleHAiOjM4MTI5MTkxMjcsImlhdCI6MTY2NTQzNTQ4MCwidXNlciI6ICJhZG1pbiJ9.6fNNMumYvQOZIH9PV1LUEDBJjhxHQLzr5ZkGMWqF-HhFR33KhwNYHEVwkOH7s1vfIYQ7XdDJQJHAguk6aBYboA",
      "customerIpAddress": "192.227.54.4",
      "authDate": "Wed, 11 Oct 2022 11:22:31 GMT"
    });
  }
}