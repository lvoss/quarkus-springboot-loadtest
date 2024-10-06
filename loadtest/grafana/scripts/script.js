import { check, sleep } from "k6";
import http from 'k6/http';

const isNumeric = (value) => /^\d+$/.test(value);

const default_vus = 50;

const target_vus_env = `${__ENV.TARGET_VUS}`;
const target_vus = isNumeric(target_vus_env) ? Number(target_vus_env) : default_vus;

export let options = {
  stages: [
      // Ramp-up from 1 to TARGET_VUS virtual users (VUs) in 5s
      { duration: "5s", target: target_vus },

      // Stay at rest on TARGET_VUS VUs for 50s
      { duration: "30s", target: target_vus  * 10 },

      // Stay at rest on TARGET_VUS VUs for 50s
      { duration: "600s", target: target_vus * 10 },

      // Stay at rest on TARGET_VUS VUs for 50s
      { duration: "10s", target: target_vus },

      // Ramp-down from TARGET_VUS to 0 VUs for 5s
      { duration: "5s", target: 0 }
  ]
};

export default function () {
  // For Google Cloud Run use Bearer Token
  const identityToken = "";
  const responseSpringJVM = http.get("https://springprimecalcopenjdk-74khbbrlba-ey.a.run.app/getPrimes?startNumber=10000000000000&count=1", {headers: {Accepts: "application/json", Authorization: identityToken}});
  const responseSpringGraalVM = http.get("https://springprimecalcgraal-74khbbrlba-ey.a.run.app/getPrimes?startNumber=10000000000000&count=1", {headers: {Accepts: "application/json", Authorization: identityToken}});
  const responseQuarkusGraalVM = http.get("https://quarkus-loadtest-74khbbrlba-ey.a.run.app/prime-calculation?startNumber=10000000000000&count=1", {headers: {Accepts: "application/json", Authorization: identityToken}});
  const responseQuarkusJVM = http.get("https://quarkus-loadtest-jvm-74khbbrlba-ey.a.run.app/prime-calculation?startNumber=10000000000000&count=1", {headers: {Accepts: "application/json", Authorization: identityToken}});
  check(responseSpringJVM, { "status is 200": (r) => r.status == 200 });
  check(responseSpringGraalVM, { "status is 200": (r) => r.status == 200 });
  check(responseQuarkusGraalVM, { "status is 200": (r) => r.status == 200 });
  check(responseQuarkusJVM, { "status is 200": (r) => r.status == 200 });
  sleep(.300);
};
