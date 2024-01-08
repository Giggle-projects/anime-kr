# Anime-kr
<p align="center">
  <a>
    <img src="https://github.com/Giggle-projects/anime-kr/assets/46060746/7ac08e5e-2f29-4d28-905f-410121abb61f" alt="Logo" width="600">
  </a>
  <p align="center">API that provides famous animation lines in Korean version, inspired by animechan </p>
  <p align="center">
    <a href="https://anime-kr.ecsimsw.com/api/counts">See request count</a>
    |
    <a href="https://github.com/Giggle-projects/anime-kr/issues/25">Add your data</a>
  </p>
</p>

## About

애니 명대사를 제공합니다.      
영문 버전 API, [animechan](https://github.com/rocktimsaikia/animechan) 을 보고 한국 애니메이션이 포함된 한국어 버전이 있었으면 좋겠다는 생각에 제작하였습니다.      
     
## Note before use
1. IP 당 1초에 5개 초과 요청은 제한돼요.
2. 페이지네이션의 첫 인덱스는 0부터, 기본 페이지 사이즈는 20 이에요.
3. 페이지의 사이즈를 지정할 수 있지만 100을 넘어선 요청은 불가능해요.
4. 누구나 데이터를 추가할 수 있습니다. [여기](https://github.com/Giggle-projects/anime-kr/issues/25)에서 좋아하는 애니의 명대사를 추가해보세요.
5. Api 의 호출 수가 궁금하면 [여기](https://anime-kr.ecsimsw.com/api/counts) 를 방문해보세요.

## How to use

### Get randomly

```
https://anime-kr.ecsimsw.com/api/anime/random
```

```
{
  "index": 79,
  "title": "슬램덩크",
  "famousLine": "영감님의 영광의 시대는 언제였죠? 국가대표였을 때였나요? 난 지금입니다."
}
```

### Get randomly from anime

```
https://anime-kr.ecsimsw.com/api/anime/random?title=원피스
```

```
{
  "index": 88,
  "title": "원피스",
  "famousLine": "사람이 언제 죽는다고 생각하나? 심장이 총알에 뚫었을 때? 아니. 불치병에 걸렸을 때? 아니. 맹독 버섯 수프를 먹었을 때? 아니.. 사람들에게서 잊혀졌을 때다..!"
}
```

### Search quotes by keyword (애니 제목)

```
https://anime-kr.ecsimsw.com/api/anime/search?title=시간
```

```
[
  {
    "index": 17,
    "title": "시간을 달리는 소녀",
    "famousLine": "미래에서 기다릴게"
  }
]
```

### Search quotes by keyword (대사)

```
https://anime-kr.ecsimsw.com/api/anime/search?line=이름
```

```
[
  {
    "index": 13,
    "title": "센과치히로의행방불명",
    "famousLine": "내 진짜 이름은 니기하야미 코하쿠누시야!"
  },
  {
    "index": 82,
    "title": "원피스",
    "famousLine": "내 이름은 우솝!! 잘 기억해 둬 톤타타. 만약 내가 죽는다면 그 때는 노랜드 옆에 동상을 세워라! 지금부터 내가 너희들의 전설의 히어로가 되어주마"
  }
]
```

### Get with pagination

```
https://anime-kr.ecsimsw.com/api/anime/search?title=나루토&line=노력&page=1&size=3
```

```
[
  {
    "index": 32,
    "title": "나루토",
    "famousLine": "자신을 믿지 않는 녀석 따위는 노력할 가치도 없다"
  },
  {
    "index": 33,
    "title": "나루토",
    "famousLine": "넌 노력의 천재다"
  }
]
```

### Get by id
```
https://anime-kr.ecsimsw.com/api/anime/18
```

```
{
  "index": 18,
  "title": "짱구는 못말려",
  "famousLine": "흰둥아. 내가 너를, 지켜줄게."
}
```

