# Geo Closest Point Of Approach Calculation

Given lat long coordinates, speed and bearing of two objects, calculates the closest point of approach (CPA). 
Speed is given in meters per second, and direction in clockwise degrees from north (though internal computation is done with coutner-clockwise cartesian degrees). Time units are in seconds.

Main methods are in the `GeoUtility` class. In particular `computeCPAtime` and `computeCPAdistance`; or `computeCPA` to receive an object representation of both time and distance for the CPA.

The methods in this class compute the CPA by translating coordinates into planar geometry, ignoring the curvature of the earth.
Therefore CPAs compute over extremely long distances (e.g. > 1000 km) might carry a significant error.

The parameter `max_time_frame` in `GeoUtility`, if greater than 0, specifies that we are only interested in CPAs in the time interval from now (0) until `max_time_frame`.
