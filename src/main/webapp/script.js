document.addEventListener('DOMContentLoaded', function() {
    const searchForm = document.getElementById('searchForm');
    const trainList = document.getElementById('trainList');

    // Set minimum date to today
    const dateInput = document.getElementById('date');
    const today = new Date().toISOString().split('T')[0];
    dateInput.min = today;

    // Sample train data (in a real application, this would come from a server)
    const sampleTrains = [
        {
            trainNumber: "12345",
            name: "Express Mail",
            departure: "08:00 AM",
            arrival: "04:30 PM",
            duration: "8h 30m",
            availableSeats: {
                "1A": 12,
                "2A": 24,
                "3A": 45,
                "SL": 120
            }
        },
        {
            trainNumber: "67890",
            name: "Super Fast Express",
            departure: "10:30 AM",
            arrival: "06:00 PM",
            duration: "7h 30m",
            availableSeats: {
                "1A": 8,
                "2A": 16,
                "3A": 32,
                "SL": 80
            }
        }
    ];

    searchForm.addEventListener('submit', function(e) {
        e.preventDefault();
        
        // Get form values
        const from = document.getElementById('from').value;
        const to = document.getElementById('to').value;
        const date = document.getElementById('date').value;
        const travelClass = document.getElementById('class').value;

        // Show loading state
        trainList.innerHTML = '<div class="loading">Searching for trains...</div>';

        // Simulate API delay
        setTimeout(() => {
            displayTrainResults(from, to, date, travelClass);
        }, 1000);
    });
});

function displayTrainResults(from, to, date, travelClass) {
    const trainList = document.getElementById('trainList');
    trainList.innerHTML = '';

    // Sample train data
    const sampleTrains = [
        {
            trainNumber: "12345",
            name: "Express Mail",
            departure: "08:00 AM",
            arrival: "04:30 PM",
            duration: "8h 30m",
            availableSeats: {
                "1A": 12,
                "2A": 24,
                "3A": 45,
                "SL": 120
            }
        },
        {
            trainNumber: "67890",
            name: "Super Fast Express",
            departure: "10:30 AM",
            arrival: "06:00 PM",
            duration: "7h 30m",
            availableSeats: {
                "1A": 8,
                "2A": 16,
                "3A": 32,
                "SL": 80
            }
        }
    ];

    if (sampleTrains.length === 0) {
        trainList.innerHTML = '<div class="no-trains">No trains found for this route. Please try different stations or date.</div>';
        return;
    }

    sampleTrains.forEach(train => {
        const trainCard = document.createElement('div');
        trainCard.className = 'train-card';
        
        trainCard.innerHTML = `
            <h3>${train.name} (${train.trainNumber})</h3>
            <div class="train-details">
                <div class="train-info">
                    <strong>Departure:</strong> ${train.departure}<br>
                    <strong>From:</strong> ${from}
                </div>
                <div class="train-info">
                    <strong>Arrival:</strong> ${train.arrival}<br>
                    <strong>To:</strong> ${to}
                </div>
                <div class="train-info">
                    <strong>Duration:</strong> ${train.duration}<br>
                    <strong>Available Seats (${travelClass}):</strong> ${train.availableSeats[travelClass]}
                </div>
                <div class="train-info">
                    <button onclick="bookTrain('${train.trainNumber}', '${train.name}', '${from}', '${to}', '${date}', '${travelClass}')" class="search-btn">
                        <span class="btn-icon">🎫</span>
                        Book Now
                    </button>
                </div>
            </div>
        `;
        
        trainList.appendChild(trainCard);
    });
}

function quickSearch(from, to) {
    document.getElementById('from').value = from;
    document.getElementById('to').value = to;
    document.getElementById('date').value = new Date().toISOString().split('T')[0];
    document.getElementById('class').value = 'SL';
    document.getElementById('searchForm').dispatchEvent(new Event('submit'));
}

function bookTrain(trainNumber, trainName, from, to, date, travelClass) {
    // Store the booking details in localStorage
    const bookingDetails = {
        trainNumber,
        trainName,
        from,
        to,
        date,
        travelClass
    };
    localStorage.setItem('currentBooking', JSON.stringify(bookingDetails));
    
    // Redirect to payment page
    window.location.href = 'payment.html';
} 