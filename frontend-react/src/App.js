import React, { useState } from 'react';
import axios from 'axios';
import './App.css';

function App() {
  const [formData, setFormData] = useState({
    cardholderName: '',
    pan: '',
    expiryDate: '',
    cvv: ''
  });
  
  const [searchType, setSearchType] = useState('pan');
  const [searchValue, setSearchValue] = useState('');
  const [searchResults, setSearchResults] = useState([]);
  const [message, setMessage] = useState('');
  const [loading, setLoading] = useState(false);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setMessage('');

    try {
      const response = await axios.post('/api/cards', formData);
      setMessage('Card created successfully!');
      setFormData({
        cardholderName: '',
        pan: '',
        expiryDate: '',
        cvv: ''
      });
    } catch (error) {
      setMessage(`Error: ${error.response?.data || error.message}`);
    } finally {
      setLoading(false);
    }
  };

  const handleSearch = async () => {
    if (!searchValue.trim()) {
      setMessage('Please enter a search value');
      return;
    }

    setLoading(true);
    setMessage('');

    try {
      let response;
      if (searchType === 'pan') {
        response = await axios.get(`/api/cards/search/pan?pan=${searchValue}`);
      } else {
        response = await axios.get(`/api/cards/search/last-four?lastFourDigits=${searchValue}`);
      }
      
      setSearchResults(response.data);
      if (response.data.length === 0) {
        setMessage('No cards found');
      }
    } catch (error) {
      setMessage(`Error: ${error.response?.data || error.message}`);
      setSearchResults([]);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <h1>Card Management System</h1>
      
      {/* Create Card Form */}
      <div className="card">
        <h2>Create New Card</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="cardholderName">Cardholder Name:</label>
            <input
              type="text"
              id="cardholderName"
              name="cardholderName"
              value={formData.cardholderName}
              onChange={handleInputChange}
              required
            />
          </div>
          
          <div className="form-group">
            <label htmlFor="pan">Card Number (PAN):</label>
            <input
              type="text"
              id="pan"
              name="pan"
              value={formData.pan}
              onChange={handleInputChange}
              pattern="[0-9]{12,19}"
              title="PAN must be 12-19 digits"
              required
            />
          </div>
          
          <div className="form-group">
            <label htmlFor="expiryDate">Expiry Date (MM/YY):</label>
            <input
              type="text"
              id="expiryDate"
              name="expiryDate"
              value={formData.expiryDate}
              onChange={handleInputChange}
              pattern="(0[1-9]|1[0-2])/([0-9]{2})"
              title="Expiry date must be in MM/YY format"
              placeholder="MM/YY"
              required
            />
          </div>
          
          <div className="form-group">
            <label htmlFor="cvv">CVV:</label>
            <input
              type="text"
              id="cvv"
              name="cvv"
              value={formData.cvv}
              onChange={handleInputChange}
              pattern="[0-9]{3,4}"
              title="CVV must be 3-4 digits"
              required
            />
          </div>
          
          <button type="submit" className="btn" disabled={loading}>
            {loading ? 'Creating...' : 'Create Card'}
          </button>
        </form>
        
        {message && (
          <div className={message.includes('Error') ? 'error' : 'success'}>
            {message}
          </div>
        )}
      </div>

      {/* Search Section */}
      <div className="card search-section">
        <h2>Search Cards</h2>
        <div className="form-group">
          <label>Search Type:</label>
          <select 
            value={searchType} 
            onChange={(e) => setSearchType(e.target.value)}
            style={{ padding: '8px', marginLeft: '10px' }}
          >
            <option value="pan">Full PAN</option>
            <option value="last-four">Last 4 Digits</option>
          </select>
        </div>
        
        <div className="form-group">
          <label htmlFor="searchValue">Search Value:</label>
          <input
            type="text"
            id="searchValue"
            value={searchValue}
            onChange={(e) => setSearchValue(e.target.value)}
            placeholder={searchType === 'pan' ? 'Enter full PAN' : 'Enter last 4 digits'}
          />
        </div>
        
        <button onClick={handleSearch} className="btn" disabled={loading}>
          {loading ? 'Searching...' : 'Search'}
        </button>
        
        {searchResults.length > 0 && (
          <div className="results">
            <h3>Search Results:</h3>
            {searchResults.map((card, index) => (
              <div key={index} className="result-item">
                <p><strong>Cardholder Name:</strong> {card.cardholderName}</p>
                <p><strong>Masked PAN:</strong> {card.maskedPan}</p>
                <p><strong>Created:</strong> {new Date(card.createdTime).toLocaleString()}</p>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}

export default App;