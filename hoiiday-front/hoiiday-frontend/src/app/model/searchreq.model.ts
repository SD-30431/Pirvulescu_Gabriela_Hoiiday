export interface SearchRequest {
    from:  string;                
    to:    string;                
    type:  'SINGLE' | 'DOUBLE' | 'EXCLUSIVE';
    rooms: number;
  }
  