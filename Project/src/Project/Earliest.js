class Earliest {
    constructor() {
      this.THRESHOLD = 1;
    }
  
    earliest(requestList, driverList) {
      const n = requestList.length;
      const k = driverList.length;
  
      const assignedDrivers = new Map();
      const unassignedRequests = requestList.slice();
  
      // Step 1: Sort the requests in increasing order of pickup time
      requestList.sort((a, b) => a.pickTime - b.pickTime);
  
      // Step 3: Assign each driver the (at most) n/k requests with the closest pickup locations
      this.assignClosestRequests(driverList, unassignedRequests, Math.floor(n / k), assignedDrivers);
  
      // Step 4: For any unassigned request, find the closest driver and assign
      for (let i = unassignedRequests.length - 1; i >= 0; i--) {
        const r = unassignedRequests[i];
        const closestDriver = this.findClosestDriver(driverList, r, Math.floor(n / k) + this.THRESHOLD);
        if (closestDriver !== null) {
          assignedDrivers.set(r, closestDriver);
          unassignedRequests.splice(i, 1); // Safe removal from array
        }
      }
  
      // Step 6: For each request in Request-List
      for (const e of requestList) {
        const d = assignedDrivers.get(e);
        // For an assigned driver
        if (d !== undefined) {
          // If pickup time hasn't passed and enough time to drive to pickup location, add request e to schedule
          if (e.pickTime > d.getCurrentTime() && this.hasEnoughTime(d, e)) {
            d.schedule.push(e);
            // Update driver's currLocation to finishPos of completed request
            d.setPosition(e.finishPos);
            d.setCurrentTime(e.finishTime);
          }
        }
      }
    }
  
    assignClosestRequests(drivers, requests, count, assignedDrivers) {
      for (const driver of drivers) {
        // Create a priority queue to hold the requests sorted by distance
        const closestRequests = requests.slice().sort((a, b) => this.dist(driver.getPosition(), a.startPos) - this.dist(driver.getPosition(), b.startPos));
  
        // Assign the closest requests to the driver
        for (let i = 0; i < count && closestRequests.length > 0; i++) {
          const request = closestRequests.shift(); // Get the closest request
          if (assignedDrivers.has(request)) {
            if (this.dist(driver.getPosition(), request.startPos) < this.dist(assignedDrivers.get(request).getPosition(), request.startPos)) {
              assignedDrivers.set(request, driver);
            }
          } else {
            assignedDrivers.set(request, driver);
          }
          requests.splice(requests.indexOf(request), 1); // Remove it from the list of requests
        }
      }
    }
  
    findClosestDriver(driverList, r, maxRequests) {
      let closestDriver = null;
      let closestDist = Number.MAX_VALUE;
      for (const driver of driverList) {
        if (driver.schedule.length < maxRequests) {
          const distance = this.dist(driver.getPosition(), r.startPos);
          if (distance < closestDist) {
            closestDriver = driver;
            closestDist = distance;
          }
        }
      }
      return closestDriver;
    }
  
    hasEnoughTime(d, r) {
      const drivingTime = this.dist(d.getPosition(), r.startPos) / 1; // Calculate driving time
      return d.getCurrentTime() + drivingTime <= r.pickTime; // Return true if driver can arrive on time
    }
}