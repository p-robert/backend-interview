### Welcome to our Java Backend Test

The purpose of this test is to simulate a real task in our programmer life.

Relax and enjoy :).

# Let the "coding" begin!

Prerequisites:

Import in your mongo the following mongo dump: [backend-interview.zip](./mongoDump/backend-interview.zip)

Tasks:

1. Clone the project and create a new branch using the pattern "interview/\<your_lastname>"

2. Add dependency for Mongo and connect to:

    ```
    URL: <your local mongo>
    PORT: 27017
    DATABASE: backend-interview
    USERNAME: <mongo username>
    PASSWORD: <mongo password>
    ```

3. Develop a rest api for monitoring your micro-service which should be available at the address:
    ```
    http://localhost:8090/interview-service/ping
    ```
    and should respond with:
    ```json
    {
      "ping": "OK"
    }
    ```

4. Open swagger.yaml in an online swagger editor [link](https://editor.swagger.io/) and implement the described API      
    Explanation for /stats/devices:
    ```
        {
          "data": [
            {
              "id": "string", //generated
              "type": "devicesStats",
              "attributes": {
                "totalDevices": 21321, // total number of devices based on given userFilter
                "totalUsers": 321231, // total number of users based on given userFilter
                "classification": [ //clasification based on "deviceSubcategoryId". For each deviceSubcategoryId should an entry in classification array
                  {
                    "deviceSubcategoryId": 101,
                    "numberOfDevices": 200
                  }
                ],
                "groups": [ //based on userFilter[groupBy]
                  {
                    "id": "string", //generated
                    "type": "devicesStatsGroup",
                    "data": {
                      "name": "<userId>/<deviceCategoryId>/<vendor>", //the value of given group. Ex: if userFilter[groupby]=userId => name: 123123
                      "classification": [
                        {
                          "deviceSubcategoryId": 101, //deviceSubCategoryId value
                          "numberOfDevices": 200 //number of devices with deviceSubcategoryId
                        }
                      ]
                    },
                    "relationships": [
                      {
                        "devices": {
                          "links": { //based on given groupBy value
                            "related": "/devices?userFilter[vendor]=Samsung or /devices?userFilter[userId]=1232341 or /devices?userFilter[deviceCategoryId]=Computers"
                          }
                        }
                      }
                    ]
                  }
                ]
              }
            }
          ],
          "meta": {
            "userFilter": {//all given userFilter
              "userId": "31242343fsf32",
              "vendor": "Samsung",
              "deviceCategoryId": 1,
              "groupBy": "userId",
              "deviceSubcategoryId": 101
            }
          }
        }
    ```
   Example:  
   Request:   
   http://localhost:8090/api/v1/stats/devices?userFilter[groupBy]=vendor&userFilter[userId]=1  
   Response
   ```
      {
             "data": [
               {
                 "id": "12324324", 
                 "type": "devicesStats",
                 "attributes": {
                   "totalDevices": 20,
                   "totalUsers": 1,
                   "classification": [ 
                       {
                           "deviceSubcategoryId" : 5,
                           "numberOfDevices" : 2
                       },
                       {
                           "deviceSubcategoryId" : 6,
                           "numberOfDevices" : 3
                       },
                       {
                           "deviceSubcategoryId" : 7,
                           "numberOfDevices" : 4
                       },
                       {
                           "deviceSubcategoryId" : 3,
                           "numberOfDevices" : 6
                       },
                       {
                           "deviceSubcategoryId" : 2,
                           "numberOfDevices" : 1,
                       },
                       {
                           "deviceSubcategoryId" : 4,
                           "numberOfDevices" : 4
                       }
                   ],
                   "groups": [
                       {
                           "id" : "12312321",
                           "type" : "devicesStatsGroup",
                           "data" : {
                               "name" : "Huawei",
                               "classification" : [
                                   {
                                       "deviceSubcategoryId" : 3,
                                       "numberOfDevices" : 2
                                   },
                                   {
                                       "deviceSubcategoryId" : 6,
                                       "numberOfDevices" : 2
                                   },
                                   {
                                       "deviceSubcategoryId" : 5,
                                       "numberOfDevices" : 1
                                   },
                                   {
                                       "deviceSubcategoryId" : 7,
                                       "numberOfDevices" : 2
                                   }
                               ]
                           },
                           "relationships" : [
                               {
                                   "devices" : {
                                       "links" : {
                                           "related" : "/devices?userFilter[vendor]=Huawei"
                                       }
                                   }
                               }
                           ]
                       },
                       {
                           "id" : "12312321",
                           "type" : "devicesStatsGroup",
                           "data" : {
                               "name" : "Samsung",
                               "classification" : [
                                   {
                                       "deviceSubcategoryId" : 4,
                                       "numberOfDevices" : 3
                                   },
                                   {
                                       "deviceSubcategoryId" : 3,
                                       "numberOfDevices" : 4
                                   }
                               ]
                           },
                           "relationships" : [
                               {
                                   "devices" : {
                                       "links" : {
                                           "related" : "/devices?userFilter[vendor]=Samsung"
                                       }
                                   }
                               }
                           ]
                       },
                       {
                           "id" : "325435",
                           "type" : "devicesStatsGroup",
                           "data" : {
                               "name" : "Apple",
                               "classification" : [
                                   {
                                       "deviceSubcategoryId" : 6,
                                       "numberOfDevices" : 1
                                   },
                                   {
                                       "deviceSubcategoryId" : 5,
                                       "numberOfDevices" : 1
                                   },
                                   {
                                       "deviceSubcategoryId" : 7,
                                       "numberOfDevices" : 2
                                   },
                                   {
                                       "deviceSubcategoryId" : 4,
                                       "numberOfDevices" : 1
                                   },
                                   {
                                       "deviceSubcategoryId" : 2,
                                       "numberOfDevices" : 1
                                   }
                               ]
                           },
                           "relationships" : [
                               {
                                   "devices" : {
                                       "links" : {
                                           "related" : "/devices?userFilter[vendor]=Apple"
                                       }
                                   }
                               }
                           ]
                       }
                   ]
                 }
               }
             ],
             "meta": {
               "userFilter": {
                 "userId": "1",
                 "groupBy": "vendor"
               }
             }
           }
    ```
5. Evaluate the swagger api and propose some improvements that you have on implemented API.


![alt text][logo]

[logo]: https://i.makeagif.com/media/11-06-2015/U2iCI_.gif "Logo Title Text 2"