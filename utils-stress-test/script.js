import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 500,
    duration: '15m'
};

export default function () {
    let url = "https://anime-kr.ecsimsw.com/api/anime/random";
    let res = http.get(url);
    check(res, { 'status was 200': (r) => r.status === 200 });
    sleep(1);
}

