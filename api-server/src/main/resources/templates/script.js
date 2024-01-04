const quotesWithImages = [
    {person: 'Albert Einstein', quote: '뭐어? 미국이 아니라 하늘나라겠지! 너희 아버진 돌아가셨어. 그것도 모르냐아?', image: '../data-images/1.jpeg'},
    {
        person: 'Steve Jobs',
        quote: 'Your work is going to fill a large part of your life, and the only way to be truly satisfied is to do what you believe is great work.',
        image: '../data-images/1.jpeg'
    },
    {person: 'Oscar Wilde', quote: 'Be yourself; everyone else is already taken.', image: '../data-images/1.jpeg'},
    // Add more quotes with images as needed
];

let currentIndex = 0;

const personImage = document.getElementById('person-image');
const quoteElement = document.getElementById('quote');
const personElement = document.getElementById('person');

function displayQuote() {
    const currentQuote = quotesWithImages[currentIndex];
    personImage.src = currentQuote.image;
    quoteElement.textContent = currentQuote.quote;
    personElement.textContent = `- ${currentQuote.person}`;
}

function nextQuote() {
    currentIndex = (currentIndex + 1) % quotesWithImages.length;
    displayQuote();
}

// Initial display
displayQuote();

// Event listener for the "Next Quote" button
document.getElementById('next-btn').addEventListener('click', nextQuote);
