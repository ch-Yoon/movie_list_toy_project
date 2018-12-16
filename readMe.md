

#  **부스트 캠프 - 네이버 API 영화 정보 검색**

## **프로젝트 설명**
* Boost Camp 1차 과제
* 네이버 검색 API를 이용하여 영화 검색 후 화면에 목록을 출력하는 어플리케이션 제작
* 사용자로부터 입력 받은 검색어로 영화 정보 검색 뒤, 검색 결과를 목록으로 표시
    
## **완성한 과제 시연 영상**
<img src="https://github.com/ch-Yoon/movie_list_toy_project/blob/readme_update/readmeimage/demo.gif" width="35%">
    
## **구현한 기능**
* **기본 구현**
	* EditText를 통해 검색어를 입력 받아 검색 버튼으로 영화 검색
	* 네이버 검색 API를 활용하여 영화 목록 수신
	* 수신한 영화 목록을 RecyclerView에 표시
	* RecyclerView에 출력되는 각 영화정보에 썸네일 이미지, 제목, 평점, 연도, 감독, 출연배우 표시
	* 목록에서 영화 선택시 해당 영화 정보 link 페이지로 이동
    
* **추가 구현**
	* MVP 패턴 적용
	* 검색 결과 무한 스크롤 구현
	* API error 처리
	* Naver API key를 binary로 구성하여 JSON file로 탑재
    
## **핵심 구조**
* **아키텍처**
    
![architecture](https://github.com/ch-Yoon/movie_list_toy_project/blob/readme_update/readmeimage/architecture.jpg)
    
## **전체 클래스 도식화**
![all_classes](https://github.com/ch-Yoon/movie_list_toy_project/blob/readme_update/readmeimage/all_classes.jpg)
    
## **Event Flow**
    
* **데이터 수신 간략 시나리오**
    
![simpleFlow](https://github.com/ch-Yoon/movie_list_toy_project/blob/readme_update/readmeimage/simpleFlow.gif)
    
* **검색 버튼 클릭 정보 요청 Flow**  
    
![initFlow](https://github.com/ch-Yoon/movie_list_toy_project/blob/readme_update/readmeimage/initDataRequestFlow.jpg)
    
* **무한 스크롤 Flow**  
    
![moreFlow](https://github.com/ch-Yoon/movie_list_toy_project/blob/reamd_update2/readmeimage/moreRequestFlow.jpg)
    
* **영화 정보 웹 이동 Flow** 
    
![moveToWebFlow](https://github.com/ch-Yoon/movie_list_toy_project/blob/readme_update/readmeimage/moveToMovieWebFlow.jpg)
    
## **개발 환경 및 사용 오픈 소스**

* **개발 환경**
	* complieSdkVersion : 28
	* minSdkVersion : 15
	* targetSdkVersion : 28
	* AndroidStudioVersion : 3.2.1
    
* **사용 오픈 소스**
	* Glide 4.8.0
	* Volley 1.1.1
	* Gson 2.8.5 
